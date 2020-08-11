package com.difficode.community.mapper;

import com.difficode.community.entity.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface LoginTicketMapper {
    @Select({"select id,user_id,ticket,status,expired from login_ticket where ticket=#{ticket}"})
    LoginTicket getLoginTicketByTicket(String ticket);

    @Insert({"insert into login_ticket(user_id,ticket,status,expired) values(#{userId},#{ticket},#{status},#{expired})"})
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int saveLoginTicket(LoginTicket loginTicket);

    @Update({"update login_ticket set status=#{status} where ticket=#{ticket}"})
    int updateLoginTicketStatus(String ticket,int status);
}
