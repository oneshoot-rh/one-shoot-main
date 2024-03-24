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

    public static double getSubscriptionPrice(SubscriptionType subscriptionType){
        switch (subscriptionType){
            case FREE:
                return 0;
            case BASIC:
                return 100;
            case PROFESSIONAL:
                return 200;
            case ENTERPRISE:
                return 500;
            default:
                return 0;
        }
    }

    // payment delay
    public static int getPaymentDelayInDays(SubscriptionType subscriptionType){
        switch (subscriptionType){
            case FREE:
                return 10;
            case BASIC:
                return 30;
            case PROFESSIONAL:
                return 30;
            case ENTERPRISE:
                return 30;
            default:
                return 0;
        }
    }
}
