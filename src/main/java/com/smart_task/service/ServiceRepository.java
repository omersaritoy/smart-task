package com.smart_task.service;


import java.util.List;

public interface ServiceRepository<Req, Res>  {
    Res register(Req entity);
    List<Res> getAll();
}
