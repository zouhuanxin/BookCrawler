package com.example.book.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.book.http.OkHttpTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 首页数据处理
 */
@Component
public class HomeDataUtil {
    @Autowired
    private OkHttpTool okHttpTool;

    /**
     * 返回成功示例
     * {"msg":"成功","code":200,"data":{"label":[{"name":"首页","url":"/"},{"name":"玄幻","url":"/sort/1_1/"},{"name":"修真","url":"/sort/2_1/"},{"name":"都市","url":"/sort/3_1/"},{"name":"穿越","url":"/sort/4_1/"},{"name":"网游","url":"/sort/5_1/"},{"name":"科幻","url":"/sort/6_1/"},{"name":"其他","url":"/sort/7_1/"},{"name":"排行榜","url":"/top/"},{"name":"全本","url":"/full/1/"}],"hot":[{"author":"我吃西红柿","intrduce":"　　我叫孟川，今年十五岁，是东宁府“镜湖道院”的当代大师兄。......","imageurl":"https://www.bqkan.com/files/article/image/38/38836/38836s.jpg","title":"我吃西红柿沧元图","url":"/38/38836/"},{"author":"宅猪","intrduce":"　　苏云怎么也没有想到，自己生活了十几年的天门镇，只有自己是人。　　他更没有想到天门镇外，方圆百里，是鼎鼎有名的无人......","imageurl":"https://www.bqkan.com/files/article/image/25/25963/25963s.jpg","title":"宅猪临渊行","url":"/25/25963/"},{"author":"天蚕土豆","intrduce":"天地为炉，万物为铜，阴阳为炭，造化为工。 气运之争，蟒雀吞龙。 究竟是蟒雀为尊，还是圣龙崛起，凌驾众生？ 这是气掌乾......","imageurl":"https://www.bqkan.com/files/article/image/0/790/790s.jpg","title":"天蚕土豆元尊","url":"/0/790/"},{"author":"王婿","intrduce":"窝囊废物的上门女婿叶飞，无意中得到太极经和生死石的传承，自此开始了不一样的人生，他医术救美，武道杀敌，不仅横......","imageurl":"https://www.bqkan.com/files/article/image/42/42882/42882s.jpg","title":"王婿入赘王婿叶凡唐若雪","url":"/42/42882/"}],"recommended":[{"recommendedTitle":"强力推荐","content":[{"author":"净无痕","title":"[玄幻]伏天氏","url":"/0/243/"},{"author":"一起成功","title":"[都市]王婿叶凡唐若雪","url":"/55/55890/"},{"author":"凤凰门","title":"[都市]林江顾心雨","url":"/57/57514/"},{"author":"辰东","title":"[玄幻]圣墟","url":"/0/178/"},{"author":"一生欢喜","title":"[都市]凤凰门林江顾心雨","url":"/53/53482/"},{"author":"特种兵之无敌战神","title":"[科幻]战神归来","url":"/8/8556/"},{"author":"唐家三少","title":"[玄幻]斗罗大陆III龙王传说","url":"/1/1496/"},{"author":"忘语","title":"[修真]凡人修仙之仙界篇","url":"/0/319/"},{"author":"忘语","title":"[修真]六迹之梦魇宫","url":"/1/1020/"}]},{"recommendedTitle":"玄幻","content":[{"author":"宅猪","title":"[玄幻]临渊行","url":"/25/25963/"},{"author":"净无痕","title":"[玄幻]伏天氏","url":"/2/2412/"},{"author":"谋生任转蓬","title":"[玄幻]我的徒弟都是大反派","url":"/76/76481/"},{"author":"天蚕土豆","title":"[玄幻]元尊","url":"/0/790/"},{"author":"莫默","title":"[玄幻]武炼巅峰","url":"/2/2714/"},{"author":"飞天鱼","title":"[玄幻]万古神帝飞天鱼","url":"/68/68609/"}]},{"recommendedTitle":"修真","content":[{"author":"洛城东","title":"[修真]绝世武魂","url":"/30/30328/"},{"author":"耳根","title":"[修真]一念永恒","url":"/1/1094/"},{"author":"言归正传","title":"[修真]我师兄实在太稳健了","url":"/73/73450/"},{"author":"耳根","title":"[修真]三寸人间","url":"/0/159/"},{"author":"爱作梦的懒虫","title":"[修真]洪荒星辰道","url":"/99860/99860174/"},{"author":"我吃西红柿","title":"[修真]莽荒纪","url":"/2/2722/"}]},{"recommendedTitle":"都市","content":[{"author":"柳岸花又明","title":"[都市]我真没想重生啊","url":"/67/67393/"},{"author":"睡觉会变白","title":"[都市]从1983开始","url":"/65/65177/"},{"author":"手握寸关尺","title":"[都市]当医生开了外挂","url":"/71/71599/"},{"author":"一起成功","title":"[都市]王婿叶凡唐若雪","url":"/55/55890/"},{"author":"浪荡邪少","title":"[都市]超级医生在都市","url":"/32/32061/"},{"author":"洛书","title":"[都市]重生之都市仙尊洛尘","url":"/64/64928/"}]},{"recommendedTitle":"穿越","content":[{"author":"孑与2","title":"[穿越]明天下","url":"/71/71264/"},{"author":"猫腻","title":"[穿越]庆余年","url":"/2/2760/"},{"author":"黄杉公子","title":"[穿越]从士兵突击开始的人生","url":"/76/76129/"},{"author":"榴弹怕水","title":"[穿越]绍宋","url":"/76/76545/"},{"author":"桃李不谙春风","title":"[穿越]红楼大贵族","url":"/68/68418/"},{"author":"诺诺宝贝","title":"[穿越]农门贵女有点冷","url":"/69/69924/"}]},{"recommendedTitle":"网游","content":[{"author":"快剑江湖","title":"[网游]是篮球之神啊","url":"/66/66853/"},{"author":"云东流","title":"[网游]我能提取熟练度","url":"/69/69881/"},{"author":"第七个魔方","title":"[网游]我有一群地球玩家","url":"/69/69034/"},{"author":"文�","title":"[网游]修仙游戏满级后","url":"/67/67042/"},{"author":"颓废龙","title":"[网游]猎魔烹饪手册","url":"/70/70233/"},{"author":"凌虚月影","title":"[网游]我的混沌城","url":"/70/70760/"}]},{"recommendedTitle":"科幻","content":[{"author":"贰蛋","title":"[科幻]重生之老子是皇帝","url":"/53/53739/"},{"author":"穿黄衣的阿肥","title":"[科幻]我的细胞监狱","url":"/65/65849/"},{"author":"间歇性诈尸","title":"[科幻]全球崩坏","url":"/60/60497/"},{"author":"兴霸天","title":"[科幻]诸天谍影","url":"/67/67968/"},{"author":"一言难尽中","title":"[科幻]娱乐圈里的钢铁直男","url":"/75/75652/"},{"author":"黑暗狗熊","title":"[科幻]我有超体U盘","url":"/77/77025/"}]}]}}
     */
    public JSONObject BqgHome() {
        JSONObject data = new JSONObject();
        String msg = okHttpTool.Get(Constant.bqg);
        if (msg == null) {
            return null;
        }
        if (msg.length() < 10) {
            return null;
        }
        Document document = Jsoup.parse(msg);
        //拿到标签信息
        Elements lableElements = document.getElementsByClass("nav").get(0).getElementsByTag("li");
        JSONArray labels = new JSONArray();
        for (int i = 1; i < lableElements.size() - 2; i++) {
            JSONObject object = new JSONObject();
            object.put("name", lableElements.get(i).text());
            object.put("url", lableElements.get(i).getElementsByTag("a").attr("href"));
            labels.add(object);
        }
        data.put("label", labels);
        //拿到首页火爆书籍
        Elements hotElements = document.getElementsByClass("hot").get(0).getElementsByClass("p10");
        JSONArray hots = new JSONArray();
        for (int i = 0; i < hotElements.size(); i++) {
            JSONObject object = new JSONObject();
            Element imageDiv = hotElements.get(i).getElementsByTag("div").get(0);
            Element textDiv = hotElements.get(i).getElementsByTag("div").get(0);
            object.put("imageurl", imageDiv.getElementsByTag("img").attr("src"));
            object.put("url", imageDiv.getElementsByTag("a").attr("href"));
            object.put("title", textDiv.getElementsByTag("dt").text());
            object.put("author", textDiv.getElementsByTag("span").text());
            object.put("intrduce", textDiv.getElementsByTag("dd").text());
            hots.add(object);
        }
        data.put("hot", hots);
        //拿到首页推荐书籍
        Elements recommendedElements = document.getElementsByClass("block");
        JSONArray recommendeds = new JSONArray();
        for (int i = 0; i < recommendedElements.size(); i++) {
            JSONObject object1 = new JSONObject();
            JSONArray array = new JSONArray();
            Elements lis = recommendedElements.get(i).getElementsByTag("ul").get(0).getElementsByTag("li");
            for (int j = 0; j < lis.size(); j++) {
                JSONObject object2 = new JSONObject();
                Elements spans = lis.get(j).getElementsByTag("span");
                object2.put("url", spans.get(1).getElementsByTag("a").get(0).attr("href"));
                object2.put("title", spans.get(0).text() + spans.get(1).text());
                object2.put("author", spans.get(2).text());
                array.add(object2);
            }
            object1.put("recommendedTitle", recommendedElements.get(i).getElementsByTag("h2").text());
            object1.put("content", array);
            recommendeds.add(object1);
        }
        data.put("recommended", recommendeds);
        return data;
    }

}
