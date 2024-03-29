package cn.edu.fudan.se;

/**
 * Created by Dawnwords on 2015/5/24.
 */
public class Parameter {
    public static final String TOPIC = "taTopic5";
    public static final String INVOKER_CONSUMER_GROUP = "invoker-cta";
    public static final String INVOKER_PRODUCER_GROUP = "invoker-pta";
    public static final String INVOKER_KEY = "request";

    public static final String RESPONSER_CONSUMER_GROUP = "responser-cta";
    public static final String RESPONSER_PRODUCER_GROUP = "responser-pta";
    public static final String RESPONSER_KEY = "response";

    public static final String RESPONSE_TAG_SCHOOL = "school_all_response";
    public static final String REQUEST_TAG_SCHOOL = "school_all_request";

    public static final String RESPONSE_TAG_STUDENT = "student_all_response";
    public static final String REQUEST_TAG_STUDENT= "student_all_request";

    public static final String RESPONSE_TAG_CLEAR = "clear_all_response";
    public static final String REQUEST_TAG_CLEAR= "clear_all_request";

    public static final String RESPONSE_TAG_COURSE = "course_response";
    public static final String REQUEST_TAG_COURSE = "course_request";

    // 课程功能
    public static final int DEFAULT = -1;
    public static final int REQUEST_SELECT = 0;//"select_request";
//    public static final String RESPONSE_TAG_SELECT = "select_response";

    public static final int REQUEST_DROP = 1;//"drop_request";
//    public static final String RESPONSE_TAG_DROP = "drop_response";

    public static final String REQUEST_TAG_SCHEDULE = "schedule_request";
    public static final String RESPONSE_TAG_SCHEDULE = "schedule_response";

    public static final int REQUEST_QUERY_BY_TIME = 3;//"query_by_time_request";
//    public static final String RESPONSE_TAG_QUERY_BY_TIME = "query_by_time_response";

    public static final int REQUEST_COURSE_INFO = 4;//"course_info_request";
//    public static final String RESPONSE_TAG_COURSE_INFO = "course_info_response";

    public static final int REQUEST_QUERY_BY_ID = 5;//"query_by_id_request";
    public static final String RESPONSE_TAG_QUERY_BY_ID = "query_by_id_response";

}
