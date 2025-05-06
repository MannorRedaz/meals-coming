package com.mannor.mealscoming.dto;

import com.mannor.mealscoming.entity.Evaluation;
import com.mannor.mealscoming.entity.Merchant;
import com.mannor.mealscoming.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EvaluationUserDto extends Evaluation {
    private User user;
    private Merchant merchant;
}
