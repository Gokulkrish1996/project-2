package com.dao;

import com.model.BlogPost;
import com.model.BlogPostLikes;
import com.model.User;

public interface BlogPostLikesDao {
	//select * from blogpostlikes where blogpost_id=? and username=?
	//if user already liked the post, 1 object
	//if user has not yet liked the post,null object
	BlogPostLikes userLikes(BlogPost blogPost,User user);
	//increment/decrement the nos of likes
	//insert into blogpostlikkes/delete from bligpost likes
	BlogPost updateLikes(BlogPost blogPost,User user);
}
