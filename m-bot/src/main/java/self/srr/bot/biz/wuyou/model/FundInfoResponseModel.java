package self.srr.bot.biz.wuyou.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Response model for fund info API
 * <p>
 * Created by Sharuru on 2021/02/19.
 */
@Data
public class FundInfoResponseModel {

    @SerializedName("ErrCode")
    int errCode;
    @SerializedName("Success")
    boolean success;
    @SerializedName("Datas")
    List<FundInfo> datas = new ArrayList<>(0);

    @Data
    public static
    class FundInfo {
        @SerializedName("PDATE")
        String valueDate;
        @SerializedName("FCODE")
        String fundCode;
        @SerializedName("SHORTNAME")
        String fundName;
        @SerializedName("NAV")
        String netValue;
        @SerializedName("ACCNAV")
        String accNetValue;
        @SerializedName("NAVCHGRT")
        String changeRate;
        @SerializedName("GSZ")
        String estValue;
        @SerializedName("GSZZL")
        String estRate;
        @SerializedName("GZTIME")
        String estTime;

    }

}
