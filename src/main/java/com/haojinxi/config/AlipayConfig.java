package com.haojinxi.config;

public class AlipayConfig {
    public static String app_id = "2021000122676242";                                                      //在后台获取（必须配置）

    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDdN416URW7Wy+dVwKK5zE4q9RBrRdvDTxsS5BzaBdEphLkdk/JpR7u3e9bAT6KUuBWoiRDFHM6zSJxYwmlChsmTn360f97YtLAFolrilFCxNQxC198gPhLktY/64EgMm1fKDF4QDEjPOyW1TWg8DgizcRC37sJfFqbELZHWp0MBp2F8tSjbhsqpgjVyJNX+p/koYwBoz7Yjmt4rsqjhVFSEm8Rtw+kibch1yMrtpiUzpwObxZV27ksVggg7s8tW0oQesHJ7wjBqUSFv46qK8zhk41hJOWZq24q4T1yN/7i6HS6o+EDayCneDJnaQHGz0rWHcO9s20k1DbUjCqxXSPPAgMBAAECggEARfhBRpQJFKal69VOl5LCrdMjotZV2ClOmE5aVTZyTaNdzGdjmzsgCpumDpnZJzVJgf1tDM6WF940a7rQnSQSLE6daE0tyuAaGunF837fbwgEubKINEAyyOyb2cHqqL1qfLOg2hEruaKWjqrHeK0ihFejISfU6A4uNmfVz8HodJm4d4gvHrs1sUDlHu75FzugUvJkHBgi2+N/yz8jLZBzP0Z/7NuK8IVbFzsJv9XhYTsJdzIJZ+usVHsN6vHjdzG69ZAP8s8suMR1pGBqMKErIfXB5oWQlAc1CQYBulx4bC95s/ekkkTUGJ0R6lCbWSxwGMMRYo1nEQO3i+0S/YGEcQKBgQD0QOllE3M5U9BmLy6u6e837gH3XnTLUteeOBd/kbYmqbLUDMgOBki23DWhsDdHws2NNnhEekm3o4XDj9lUn2R+Puuxh8IxJKc+gM6bXVrVKVRpZMLwPENOBo7swLoObYPYzsEeb44FvmJpb2lLbOIUVbnTmmoizFSw3ZtNO2XaaQKBgQDn2wey4erMJasvLowPZ6BJ+7PiEKKW/aOW3eEnBifUGdv9oN6PD2lznMHpe2cxnGNnUKcrDH/caKyrsB8YHOYVvbywnEpdOh7UpYm2BQKEp5Nhb22XPUZsCowBIY1RSR68KVJu3xTo2eFgywOn9D2YP4O8sWxeYmSeT4GeYgoVdwKBgHyKPHcVG8M3QSNZG2yESz0xVj9TIiop61b5MsIRAddk35HPkpHUEm9qiM4Qk587bKN8Coaf88Z20lWMmzvSqeLVIkg6+IEmN0BfXPlK+G2HzUqP3fWwRIEcRvPgsP16SafahA2Iw9mZ4YPxErcqpXvI2h9StJtZpDUqSW1vw9dBAoGAJU5P2c+SCRESjz/tF1l0KBNQ6VIF50eed9rxrGbIJ8+VXyTwSpKPRCP364qImynvsKMAkuOq+xr4lZN8HVQhDWR/JOew63k7lrmgA+KgehnAlA/q0UfxudakmlX8uBSM1nfLpL1OTv0nz3w69V5OPt4RY14k8rbgO7zNPV/HAZcCgYEA0oY7PEMHFM5xQ0JcXJIdgKMLddhFLJioXvyW5obP24IQkFRAvsh0DDTE62aUQzNvK7hIXP+2s4i2afewZBd5U6yKHNRblGVRwPdzpgqYlYkw4Ke+wF0K/DD0+T+xCJsEfjyLZiPduu9PfdngdWVwQhx0orN128DNBL9QhmEYtLw=";

    public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm805e493IVzbIa+mowAPpJpJph1gOa4CzIN+sqYqVi7UW4UKUHm+zqkcXcQgaORj7FmDTZ3oLvdZVZVkOYh1cSCv8qzVI5aO2vwlnygygH3jeFc64UV/fa1r0fGxHP1emDhnoHxKbsTfudDuXAMCoKfh9nKTt0j8YQ4usIGr6ZMEptLFQEvk91goqBzJfu0ogYrCLQ0Sdyu6mszpE3AtZ2/LESsjY0nZRuOc6Z/NYcA/REe5WsGMAFzGXi54yZf/K1NDh99X1T3ekComnf6OGsJN7MUdR6m67WRglPQ0M3lRo7yvedl5+EB6tyggdODSkAtGkQHaIiO2/348wEjFPwIDAQAB";

    public static String notify_url = "http://localhost:1314/alipayNotifyNotice";

    public static String return_url = "http://localhost:1314/alipayReturnNotice";

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";

    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";                         //注意：沙箱测试环境，正式环境为：https://openapi.alipay.com/gateway.do
}

