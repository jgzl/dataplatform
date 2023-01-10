package cn.cleanarch.dp.flink;

import lombok.Data;

import java.util.List;

@Data
public class DataObjectSyncDO {
    private String sourceTable;
    private String targetTable;
    private String sourceDb;
    private String targetDb;
    private List<ColumnMapping> columnMappingList;
}
