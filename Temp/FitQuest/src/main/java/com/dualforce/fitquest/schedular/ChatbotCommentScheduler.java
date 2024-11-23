package com.dualforce.fitquest.schedular;

import com.dualforce.fitquest.service.commentbot.CommentBotService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ChatbotCommentScheduler {
    private final CommentBotService commentBotService;

    private static final Logger logger = LoggerFactory.getLogger(ChatbotCommentScheduler.class);

    public ChatbotCommentScheduler(CommentBotService commentBotService) {
        this.commentBotService = commentBotService;
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void runChatbotCommentScheduler() {
        logger.info("ChatbotCommentScheduler 실행 시작...");
        try {
            commentBotService.processChatbotComments();
            logger.info("ChatbotCommentScheduler 실행 완료");
        } catch (Exception e) {
            logger.error("ChatbotCommentScheduler 실행 중 오류 발생", e);
        }
    }
}
