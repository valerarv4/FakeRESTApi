package net.azurewebsites.fakerestapi.models.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResponseDto {

    @EqualsAndHashCode.Exclude
    private String type;
    private String title;
    private Integer status;
    @EqualsAndHashCode.Exclude
    private String traceId;
    @EqualsAndHashCode.Exclude
    private Map<String, List<String>> errors;

    public ErrorResponseDto(String title, int status) {
        this.title = title;
        this.status = status;
    }
}
