package ma.oneshoot.oneshootmain.utils;

import ma.oneshoot.oneshootmain.tenant.SubscriptionType;

public class SubscriptionUtil {

    public static int getSubscriptionDurationInDays(SubscriptionType subscriptionType){
        switch (subscriptionType){
            case FREE:
                return 30;
            case BASIC:
                return 90;
            case PROFESSIONAL:
                return 180;
            case ENTERPRISE:
                return 365;
            default:
                return 0;
        }
    }
}
