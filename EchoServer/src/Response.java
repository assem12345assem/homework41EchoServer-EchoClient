import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public enum Response {
    DATE ("date") {
        @Override
        public String respond(String[] str) {
            return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
    },
    TIME ("time") {
        @Override
        public String respond(String[] str) {
            LocalTime l = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            return l.toString();
        }
    },
    REVERSE ("reverse") {
        @Override
        public String respond(String[] str) {
            StringBuilder sb = new StringBuilder();
            if(str.length > 1) {
                for (int i = 1; i < str.length; i++) {
                    sb.append(str[i]).append(" ");
                }
                sb.deleteCharAt(sb.length()-1);
                sb.reverse();
                return sb.toString();
            } else {
                return "There is no string to reverse!";
            }
        }
    },
    UPPER ("upper"){
        @Override
        public String respond(String[] str) {
            StringBuilder sb = new StringBuilder();
            if(str.length > 1) {
                for (int i = 1; i < str.length; i++) {
                    sb.append(str[i]).append(" ");
                }
                sb.deleteCharAt(sb.length()-1);
                return sb.toString().toUpperCase();
            } else {
                return "There is no string to reverse!";
            }
        }
    },
    BYE ("bye") {
        @Override
        public String respond(String[] str) {
            return null;
        }
    };

    final String value;

    Response(String value) {
        this.value = value;
    }

    public abstract String respond(String[] str);

    public static String serverAction(String message) {
        String[] str = message.split(" ");
        String s = str[0].toLowerCase();
        for (int i = 0; i < Response.values().length; i++) {
            if (Response.values()[i].value.compareTo(s) == 0) {
               s = Response.values()[i].respond(str);
               break;
            }
        }
        return s;
    }
}
