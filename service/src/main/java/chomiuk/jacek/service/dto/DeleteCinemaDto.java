package chomiuk.jacek.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.util.report.qual.ReportCall;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteCinemaDto {
    private Long id;
}
