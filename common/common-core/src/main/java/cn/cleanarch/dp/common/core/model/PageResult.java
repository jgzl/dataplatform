package cn.cleanarch.dp.common.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<T> data;

    public PageResult(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }
}
