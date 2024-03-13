package com.hdu.hdufpga.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRecord<O> {
    Long current;
    Long size;
    Long total;
    List<O> object;
}
