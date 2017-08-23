package self.srr.m2g.biz;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import self.srr.m2g.model.PushBizParam;
import self.srr.m2g.model.TaskModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharuru on 2017/08/23.
 */
@Service
public class PushBiz {

    // TODO
    public void push(PushBizParam param, List<TaskModel> tasks) {
        try {
            HttpPost httpPost = new HttpPost(param.getRemoteUrl());

            for (TaskModel t : tasks) {

                httpPost.setHeader("PRIVATE-TOKEN", param.getRemoteToken());

                List<NameValuePair> params = new ArrayList<>();

                params.add(new BasicNameValuePair("title", t.getTaskId() + " / " + t.getParentTaskName() + " / " + t.getFunctionName() + " / " + t.getTaskName() + " / " + t.getOrigTaskType()));
                StringBuilder desc = new StringBuilder("Source: " + t.getTaskName());
                if (!t.getRelyTasks().isEmpty()) {
                    desc.append("\n\nHave rely tasks!");
                    for (int i = 0; i < t.getRelyTasks().size(); i++) {
                        TaskModel tt = t.getRelyTasks().get(i);
                        desc.append("\n\n").append(i).append(". ").append(tt.getTaskName()).append(" / ").append(tt.getTaskId()).append(" / ").append(tt.getFunctionName()).append(" / ").append(tt.getOrigTaskType()).append(" / ").append(tt.getFinishDate()).append(" / ").append(tt.getTaskPercentage()).append(" / ").append(tt.getResourceName());
                    }
                }
                desc.append("\n\n").append("Updated at: ").append("ISSUE OPEN DATE");
                params.add(new BasicNameValuePair("description", desc.toString()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                params.add(new BasicNameValuePair("due_date", sdf.format(t.getFinishDate())));
                params.add(new BasicNameValuePair("charset", "UTF-8"));

                httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                HttpResponse response = HttpClients.createDefault().execute(httpPost);
            }
        } catch (Exception e) {
            System.out.println("-----");
            System.out.println("PUSH_EXCEPTION_DETECTED.");
        }
    }

}
