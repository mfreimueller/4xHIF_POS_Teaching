package at.spengergasse._4xhif.patterns;

public class NotificationService {
    public void sendNotification(NotificationCommand cmd) {
        if (cmd.type() == NotificationType.EMAIL) {
            System.out.println("Sending E-Mail: " + cmd.message());
        } else if (cmd.type() == NotificationType.SMS) {
            System.out.println("Sending SMS: " + cmd.message());
        } else if (cmd.type() == NotificationType.PUSH) {
            System.out.println("Sending Push Notification: " + cmd.message());
        } else if (cmd.type() == NotificationType.SLACK) {
            System.out.println("Sending Slack Message: " + cmd.message());
        } else {
            throw new IllegalArgumentException("Unknown notification type");
        }
    }
}
