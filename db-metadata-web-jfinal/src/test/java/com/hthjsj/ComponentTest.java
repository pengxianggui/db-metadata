package com.hthjsj;

import com.hthjsj.web.component.TableView;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ComponentTest {

    public static void main(String[] args) {
        AnalysisConfig analysisConfig = new AnalysisConfig();
        analysisConfig.start();
        TableView tableView = new TableView();
        tableView.setGlobal("hahaa", "xixi");
        Record record = new Record();
        record.set("id", new Date().getTime());
        record.set("config", tableView.config());
        record.set("en", tableView.code());
        record.set("cn", tableView.name());
        Db.save("meta_component", "id", record);
    }
}
