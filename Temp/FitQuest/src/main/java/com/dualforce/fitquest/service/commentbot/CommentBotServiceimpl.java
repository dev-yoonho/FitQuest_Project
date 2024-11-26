package com.dualforce.fitquest.service.commentbot;

import com.dualforce.fitquest.model.dao.PostDao;
import com.dualforce.fitquest.model.dto.CommentDto;
import com.dualforce.fitquest.service.comment.CommentService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentBotServiceimpl implements CommentBotService {
    private final PostDao postDao;
    private final CommentService commentService;
    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "당신은 운동에 대한 전문 지식을 갖춘 친절한 상담가입니다. 사용자의 질문에 대해 신뢰할 수 있는 정보와 조언을 제공하여 그들의 운동 목표 달성에 도움을 주십시오. 답변은 한국어로 작성하며, 3문단 이내로 간결하고 명확하게 전달해 주세요.";

    public CommentBotServiceimpl(PostDao postDao, CommentService commentService, ChatClient.Builder chatClientBuilder) {
        this.postDao = postDao;
        this.commentService = commentService;
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public List<Integer> findEligiblePosts() {
        return postDao.selectEligiblePosts();
    }

    @Override
    public void addChatbotCommentToPost(int postId, String comment) {
        CommentDto chatbotComment = CommentDto.builder()
                .postId(postId)
                .userId(1) // 챗봇 ID는 무조건 1
                .content(comment)
                .build();
        commentService.createComment(chatbotComment);
    }

    @Override
    public void processChatbotComments() {
        List<Integer> eligiblePosts = findEligiblePosts();
        if (eligiblePosts.isEmpty()) {
            System.out.println("No eligible posts found for chatbot comments.");
            return;
        }
        for (Integer postId : eligiblePosts) {
            try {
                // 답변이 필요한 게시글 내용
                String postContent = postDao.selectPostById(postId).getContent();
                // Spring AI OpenAI를 이용해 게시글 내용을 전송하고 comment 내용을 생성해야 함
                String chatbotResponse = generateCommentFromAI(postContent);

                addChatbotCommentToPost(postId, chatbotResponse);
            } catch (Exception e) {
                System.err.println("Failed to process post ID: " + postId + ". Error: " + e.getMessage());
            }
        }
    }

    private String generateCommentFromAI(String postContent) {
        try {
            SystemMessage systemMessage = new SystemMessage(SYSTEM_PROMPT);
            UserMessage userMessage = new UserMessage(postContent);
            List<Message> messages = List.of(systemMessage, userMessage);
            Prompt prompt = new Prompt(messages);
            return chatClient.prompt(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            System.err.println("Failed to generate comment from AI. Error: " + e.getMessage());
            return "챗봇 답변을 생성하는 데 실패했습니다.관리자에게 문의바랍니다.";
        }
    }
}
