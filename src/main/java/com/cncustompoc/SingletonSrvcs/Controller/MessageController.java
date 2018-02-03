/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cncustompoc.SingletonSrvcs.Controller;

import com.cncustompoc.SingletonSrvcs.Common.CommonParams;
import com.cncustompoc.SingletonSrvcs.domains.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/")
public class MessageController {
	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	@Autowired
	private RestTemplate restTemplate;
	@Bean RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	@Value("${hw.hcm.password}")
	private String hcmpwd;
	@Value("${hw.hcm.username}")
	private String hcmuser;
	private static String lasttime="2016-01-10T18:53:22.000Z";

	@GetMapping
	public ModelAndView list() {
		return new ModelAndView("messages/list", "messages", CommonParams.messageList);
	}

	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") String messageid) {
		Message message=new Message();
		for(Message msg:CommonParams.messageList){
			if(msg.getMessageid().equals(messageid.trim())){
				message=msg;
			}
		}
		return new ModelAndView("messages/view", "message", message);
	}


}
