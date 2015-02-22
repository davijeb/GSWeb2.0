package hello;

import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import xap.tutorial.web.space.data.UserData;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        GigaSpace gs = null;
        GigaSpace gigaSpace = new GigaSpaceConfigurer(
                new UrlSpaceConfigurer("jini://*/*/xapTutorialSessionSpace"))
                .gigaSpace();

        SQLQuery<UserData> query = new SQLQuery<UserData>(UserData.class,"");
        UserData[] data = gigaSpace.readMultiple(query);

        StringBuffer b = new StringBuffer();
        for(UserData d : data) {
            b.append(d.getName() + "<br>");
        }

        return b.toString();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
