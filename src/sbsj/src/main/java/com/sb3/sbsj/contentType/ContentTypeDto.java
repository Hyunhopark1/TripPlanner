package com.sb3.sbsj.contentType;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContentTypeDto implements IContentType {
    private Long id;
    private String name;
}
