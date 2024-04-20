package com.writeabyte.services;

import com.writeabyte.repository.CommentRepository;
import com.writeabyte.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;
}