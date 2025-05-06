package com.mannor.mealscoming.dto;

import com.mannor.mealscoming.entity.Complaint;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ComplaintUserDto  extends Complaint{
    private User user;
    private Merchant merchant;
}
