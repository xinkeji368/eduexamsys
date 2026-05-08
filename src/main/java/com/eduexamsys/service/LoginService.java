package com.eduexamsys.service;

import com.eduexamsys.entity.Admin;
import com.eduexamsys.entity.Student;
import com.eduexamsys.entity.Teacher;

public interface LoginService {

    public Admin adminLogin(Integer username, String password);

    public Teacher teacherLogin(Integer username, String password);

    public Student studentLogin(Integer username, String password);
}
