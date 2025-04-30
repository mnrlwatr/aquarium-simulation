package org.fp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Position {
     int x;
     int y;
}
