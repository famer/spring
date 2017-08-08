package com.timurtatarshaov.demoforum.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.timurtatarshaov.demoforum.dao.PostRepository;
import com.timurtatarshaov.demoforum.model.Post;
import com.timurtatarshaov.demoforum.model.Topic;
import com.timurtatarshaov.demoforum.security.UserCredential;

@Controller
public class PostController {

	@Autowired
	private PostRepository postRepository;

	/*@RequestMapping(value = "/topics/{topic_id}/", method=RequestMethod.GET)
	public String posts(@PathVariable(value="topic_id") Topic topic, @PageableDefault(sort = { "id" }, value = 2) Pageable pageRequest, Model model) {
		model.addAttribute("postsPage", postRepository.findByTopic(topic, pageRequest));
		model.addAttribute("topic", topic);
		return "post/index";
	}*/
	@RequestMapping(value = "/topics/", params = "topic_id", method=RequestMethod.GET)
	public String posts(@RequestParam(value="topic_id") Topic topic, @PageableDefault(sort = { "date" }, value = 2) Pageable pageRequest, Model model) {
		model.addAttribute("postsPage", postRepository.findByTopic(topic, pageRequest));
		model.addAttribute("topic", topic);
		
		Post post = new Post();
		post.setTopic(topic);
		model.addAttribute("postForm", post);
		
		return "post/index";
	}
	
	@RequestMapping(value = "/topics/post", params = "topic_id", method=RequestMethod.GET)
	public String postForm(@RequestParam(value="topic_id") Topic topic, Model model) {
		Post post = new Post();
		post.setTopic(topic);
		model.addAttribute("postForm", post);
		return "post/post";
	}
	
	@RequestMapping(value = "/topics/post", params = "topic_id", method=RequestMethod.POST)
	public String post(@ModelAttribute("postForm") Post postForm, @RequestParam(value="topic_id") Topic topic, BindingResult bindingResult, @AuthenticationPrincipal UserCredential activeUser, Model model) {
		if (bindingResult.hasErrors()) {
            return "post/post";
        }
		
		topic.setLastUpdated(new Date());
		postForm.setTopic(topic);
		postForm.setUser(activeUser.getUser());
		postRepository.save(postForm);
		
		return "redirect:/topics/?topic_id="+topic.getId();
	}
	

	@PreAuthorize("#post.user.username == authentication.name OR hasRole('ADMIN')")
	@RequestMapping(value = "/topics/posts/remove", method=RequestMethod.GET)
	public String remove(@RequestParam(value="post_id") Post post) {
		Long topicId = post.getTopic().getId();
		
		postRepository.delete(post);
		
		return "redirect:/topics/?topic_id="+topicId;
	}
	
	
}
