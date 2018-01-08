package com.dao;

import java.util.List;

import com.model.BlogComment;
import com.model.BlogPost;

public interface BlogPostDao {
void saveBlogPost(BlogPost blogpost);
//return list ofblogs waiting of approval / list of blogs approved
//getBlog(0) ->list of blogs waiting for approval
//getBlog(1) ->list of blogs approved
List<BlogPost> getBlogs(int approved); //values =0/1
BlogPost getBlogById(int id);
void updateBlogPost(BlogPost blogPost,String rejectionReason);
void addComment(BlogComment blogComment);
}

