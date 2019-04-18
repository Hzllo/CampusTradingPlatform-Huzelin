package com.tradingPlatform.service.impl;

import com.tradingPlatform.mapper.CommentMapper;
import com.tradingPlatform.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

}
