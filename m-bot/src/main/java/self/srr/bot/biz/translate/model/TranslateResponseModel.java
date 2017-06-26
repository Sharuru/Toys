package self.srr.bot.biz.translate.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Response model for translate API
 * <p>
 * Created by Sharuru on 2017/06/23.
 */
@Data
public class TranslateResponseModel {

    String error_code;
    String error_msg;

    String from;
    String to;
    List<TranslateResult> trans_result = new ArrayList<>();

}

