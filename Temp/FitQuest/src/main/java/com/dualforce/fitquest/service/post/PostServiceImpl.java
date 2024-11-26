package com.dualforce.fitquest.service.post;

import com.dualforce.fitquest.model.dao.PostDao;
import com.dualforce.fitquest.model.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostDao postDao;

    public PostServiceImpl(PostDao postDao) {
        this.postDao = postDao;
    }

    @Override
    public int createPost(PostDto post) {
        postDao.insertPost(post);
        return post.getPostId();
    }

    @Override
    public List<PostDto> readAllPosts() {
        return postDao.selectPostByConditions(
                null,
                null,
                null,
                null,
                "created_at_reverse",
                0,
                20
        );
    }

    @Override
    public int editPost(PostDto post) {
        if (postDao.selectPostById(post.getPostId()) == null) {
            throw new IllegalArgumentException("Post not found with ID: " + post.getPostId());
        }
        return postDao.updatePost(post);
    }

    @Override
    public int deletePost(int postid) {
        if (postDao.selectPostById(postid) == null) {
            throw new IllegalArgumentException("Post not found with ID: " + postid);
        }
        return postDao.deletePost(postid);
    }

    @Override
    public List<PostDto> readConditionSearchPosts(String title, String content, String category, String nickname, String sortBy, int offset, int limit) {
        return postDao.selectPostByConditions(
                title,
                content,
                category,
                nickname,
                sortBy,
                offset,
                limit
        );
    }

    @Override
    public PostDto readPost(int postid) {
        PostDto post = postDao.selectPostById(postid);
        if (post == null) {
            throw new IllegalArgumentException("Post not found with ID: " + postid);
        }
        postDao.incrementViews(postid);
        return post;
    }

    @Override
    public void recommendPost(int postid) {
        PostDto post = postDao.selectPostById(postid);
        if (post == null) {
            throw new IllegalArgumentException("Post not found with ID: " + postid);
        }
        postDao.incrementLikes(postid);
    }

    @Override
    public int countTotalPosts(String title, String content, String category, String nickname) {
        return postDao.countPostsByConditions(title, content, category, nickname);
    }
}
