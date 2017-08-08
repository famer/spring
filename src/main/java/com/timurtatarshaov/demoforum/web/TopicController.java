package com.timurtatarshaov.demoforum.web;

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

import com.timurtatarshaov.demoforum.dao.TopicRepository;
import com.timurtatarshaov.demoforum.model.Topic;
import com.timurtatarshaov.demoforum.security.UserCredential;

@Controller
@RequestMapping("/topics")
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;
	
	@RequestMapping("/")
	public String topics(@PageableDefault(sort = { "lastUpdated" }, value = 2) Pageable pageRequest, Model model) {
		model.addAttribute("topicsPage", topicRepository.findAll(pageRequest));
		return "topic/index";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String post(Model model) {
		model.addAttribute("topicForm", new Topic());
		return "topic/post";
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(@ModelAttribute("topicForm") Topic topicForm, BindingResult bindingResult, @AuthenticationPrincipal UserCredential activeUser, Model model) {
		if (bindingResult.hasErrors()) {
            return "topic/post";
        }
		topicForm.setUser(activeUser.getUser());
		topicRepository.save(topicForm);
		return "redirect:/topics/?topic_id="+topicForm.getId();
	}
	
	@PreAuthorize("#topic.user.username == authentication.name OR hasRole('ADMIN')")
	@RequestMapping(value = "/remove", method=RequestMethod.GET)
	public String remove(@RequestParam(value="topic_id") Topic topic) {
		
		topicRepository.delete(topic);
		
		return "redirect:/topics/";
	}
	
	
}
