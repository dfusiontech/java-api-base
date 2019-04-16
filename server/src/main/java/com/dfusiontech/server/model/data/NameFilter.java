package com.dfusiontech.server.model.data;

import lombok.*;

/**
 * Implementation of Name Filtering Logic
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-04
 */
@NoArgsConstructor
@Setter
@Getter
@ToString(of = {"name"})
@EqualsAndHashCode(of = {"name"}, callSuper = false)
public class NameFilter extends BaseFilter<Long> {
	private String name;
}
