package t1707m.spring.dto;

import java.lang.reflect.Field;
import java.util.HashMap;

public class RestResponse {

    private int status;
    private String message;
    private HashMap<String, Object> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public static final class Builder {
        private int status;
        private String message;
        private HashMap<String, Object> data;

        private Builder() {
        }

        public static Builder aRestResponse() {
            return new Builder();
        }

        public Builder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withData(HashMap<String, Object> data) {
            this.data = data;
            return this;
        }

        public Builder withData(Object obj) {
            Field[] fields = obj.getClass().getDeclaredFields();
            this.data = new HashMap<>();
            try {
                for (Field field :
                        fields) {
                    field.setAccessible(true);
                    this.data.put(field.getName(), field.get(obj));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return this;
        }

        public RestResponse build() {
            RestResponse restResponse = new RestResponse();
            restResponse.data = this.data;
            restResponse.status = this.status;
            restResponse.message = this.message;
            return restResponse;
        }
    }
}
