package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class EditUrlResponse extends Response{

        public static EditUrlResponse success() {
            return success(builder().build());
        }

        public static EditUrlResponse failed(Error error) {
            return failed(builder().build(), error);
        }
}