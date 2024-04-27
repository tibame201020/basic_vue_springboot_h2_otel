<template>
  <div class="container">
    <div class="row">
      <div class="col-12">
        <el-button @click="fetchAllRecords">fetch all records</el-button>
      </div>
    </div>
    <div class="row" v-show="accountStore.records.length">
      <div class="col-12 chartParent">
        <div class="chart" ref="chartRef"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import useAccountStore from "../stores/account-store";
import * as echarts from "echarts";
import type { EChartsOption } from "echarts";
import { ref, onMounted, watch } from "vue";

const accountStore = useAccountStore();
const chartRef = ref(null);

const groupByKey = <T>(array: T[], getKey: (item: T) => string) => {
  return array.reduce((acc, curr) => {
    const key = getKey(curr);
    if (!acc[key]) {
      acc[key] = [];
    }
    acc[key].push(curr);
    return acc;
  }, {} as { [key: string]: T[] });
};

const buildOption = (
  xAxisData: string[],
  data: { [key: string]: any[] }
): EChartsOption => {
  return {
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
    },
    legend: {
    },
    grid: {
      left: "3%",
      right: "4%",
      bottom: "3%",
      containLabel: true,
    },
    xAxis: [
      {
        type: "category",
        data: xAxisData,
      },
    ],
    yAxis: [
      {
        type: "value",
      },
    ],
    series: [
      {
        name: 'httpError',
        type: "bar",
        data: Object.entries(data)
          .map(([, records]) => (records).filter(record => !record.isError))
          .map(records => records.length),
        itemStyle: {
          color: 'lightcoral'
        },
      },
      {
        name: 'httpOk',
        type: "bar",
        data: Object.entries(data)
          .map(([, records],) => (records).filter(record => record.isError))
          .map(records => records.length),
        itemStyle: {
          color: 'lightgreen'
        },
      },
      {
        name: 'total',
        type: "bar",
        data: Object.entries(data).map(([, records]) => (records.length)),
        itemStyle: {
          color: 'lightskyblue'
        },
      },
    ]
  }
};

const initChart = () => {
  const data = groupByKey(accountStore.records, (record) => record.userEmail);
  const xAxisData = Object.keys(data);
  const chart = echarts.init(chartRef.value);
  chart.setOption(buildOption(xAxisData, data));
  chart.resize()
};

onMounted(() => {
  initChart()
});

const fetchAllRecords = async () => {
  await accountStore.fetchAllRecords()
  initChart()
}

</script>

<style scoped>
.chart {
  width: 1000px;
  height: 100%;
}

.chartParent {
  min-height: 500px;
}
</style>
