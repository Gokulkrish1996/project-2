package com.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.BlogPostDao;
import com.dao.BlogPostLikesDao;
import com.dao.UserDao;
import com.model.BlogComment;
import com.model.BlogPost;
import com.model.BlogPostLikes;
import com.model.ErrorClazz;
import com.model.User;

@RestController
public class BlogPostController {
	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private BlogPostLikesDao blogPostLikesDao;
	@Autowired
	private UserDao userDao;
	@RequestMapping(value="/saveblog",method=RequestMethod.POST)
	public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String username=(String) session.getAttribute("username");
		if(username==null)
		{
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
			}
		User user=userDao.getUserByUsername(username); //select * from user when usernmae="uma"
				blogPost.setPostedOn(new Date());
		blogPost.setPostedBy(user); //FK column posted_by username ['ruba']
		try{
			blogPostDao.saveBlogPost(blogPost);
		}catch(Exception e)
		{
			ErrorClazz error=new ErrorClazz(6,"Unable to insert blog details" +e.getMessage());
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR); //500
		}
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
@RequestMapping(value="/getblogs/{approved}",method=RequestMethod.GET)
public ResponseEntity<?> getBlogs(@PathVariable int approved,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
		}
	
	if(approved==0) //list of blog waiting for approval
	{
		User user=userDao.getUserByUsername(username);
	if(!user.getRole().equals("ADMIN")){
			ErrorClazz error=new ErrorClazz(7,"acess denied");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
}
			List<BlogPost> blogPosts=blogPostDao.getBlogs(approved);
	return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
		
}
@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
public ResponseEntity<?> getBlogPost(@PathVariable int id,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
		}
	BlogPost blogPost=blogPostDao.getBlogById(id);
	return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
}
@RequestMapping(value="/updateapprovalstatus",method=RequestMethod.PUT)
public ResponseEntity<?> updateApprovalStatus(@RequestBody BlogPost blogPost,
		   @RequestParam(required=false) String rejectionReason,HttpSession session)
{
	String username=(String) session.getAttribute("username");
	if(username==null)
	{
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
		}
	try{
		//if admin selects Approve, blogPost.approved=1
		//if admin selects Rejects, blogPost.approved=0
		blogPostDao.updateBlogPost(blogPost,rejectionReason);
	}catch(Exception e)
	{
		ErrorClazz error=new ErrorClazz(7,"Unable to update blogpost approval status");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR); //500
	}
	return new ResponseEntity<Void>(HttpStatus.OK);
		

}
@RequestMapping(value="/userLikes/{id}",method=RequestMethod.GET)
public ResponseEntity<?> userLikes(@PathVariable int id,HttpSession session)
{
	String username=(String)session.getAttribute("username");
	if(username==null)
	{
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
		}
	User user=userDao.getUserByUsername(username);
	BlogPost blogPost=blogPostDao.getBlogById(id);
	//blodPostLikes=null/1 object
	//if user has not yet liked the blogPost.blogPostLikes = null
	//if user has not yet liked the blogPostalready,blogPost = 1 obj
	BlogPostLikes blogPostLikes=blogPostLikesDao.userLikes(blogPost, user);
	return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);
}
@RequestMapping(value="/updatelikes",method=RequestMethod.PUT)
public ResponseEntity<?> updateLikes(@RequestBody BlogPost blogPost,HttpSession session)
{
	String username=(String)session.getAttribute("username");
	if(username==null)
	{
		ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
		return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
		}
	User user=userDao.getUserByUsername(username);
	BlogPost updatedBlogPost=blogPostLikesDao.updateLikes(blogPost, user);
	return new ResponseEntity<BlogPost>(updatedBlogPost,HttpStatus.OK);
}
@RequestMapping(value="/addcomment",method=RequestMethod.POST)
public ResponseEntity<?> addBlogComment(@RequestParam String commentText,@RequestParam int id,HttpSession session)
{
String username=(String)session.getAttribute("username");
if(username==null)
{
	ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
	return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //401
	}

User commentedBy=userDao.getUserByUsername(username);
//construct blogcomment object
BlogComment blogComment=new BlogComment();
blogComment.setCommentText(commentText);
blogComment.setCommentedBy(commentedBy);
BlogPost blogPost=blogPostDao.getBlogById(id); //newly added blogcomment
blogComment.setBlogPost(blogPost);
blogComment.setCommentedOn(new Date());
try{
	blogPostDao.addComment(blogComment);
}
catch(Exception e)
{
	ErrorClazz error=new ErrorClazz(7,"Unable to post comment" + e.getMessage());
	return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR); //500
}
return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
}
}
