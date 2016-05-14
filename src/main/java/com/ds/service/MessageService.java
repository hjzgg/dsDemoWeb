package com.ds.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ds.utils.ReaderXML;
import com.yonyou.uap.entity.content.EmailContent;
import com.yonyou.uap.entity.content.MessageContent;
import com.yonyou.uap.entity.receiver.MessageReceiver;
import com.yonyou.uap.entity.response.MessageResponse;
import com.yonyou.uap.service.MessageSend;

@Service
public class MessageService {
	public List<MessageResponse> sendEmail(String title, String content, String address, String baseUrl) {
		List<MessageResponse> responseList = new ArrayList<MessageResponse>();
		String xmlPath = Thread.currentThread().getContextClassLoader().getResource("myMailTemplete.xml").getPath();
		String xmlContent = new ReaderXML().read(xmlPath);  
        Object[] obj=new Object[]{content, baseUrl + "images/left.gif",
        		baseUrl + "images/right.gif", baseUrl + "images/f.gif",
        		baseUrl + "images/bird.gif", baseUrl + "images/top.gif"};  
        //MessageFormat���Ը�ʽ����������Ϣ��Ȼ�󽫸�ʽ������ַ������뵽ģʽ�е��ʵ�λ��
        String tcontent = MessageFormat.format(xmlContent, obj);
		MessageReceiver receiver = new MessageReceiver(address);
		MessageContent msgContent = new EmailContent(title, tcontent);
		responseList.addAll(new MessageSend(receiver, msgContent).send());
		return responseList;
	}
}
