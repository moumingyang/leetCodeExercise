package com.mmy.leetcode.medium;

import org.junit.Test;

/**
 * @author: mmy
 * @date: 2018/08/10
 * @description:
 */
public class Medium9 {

  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int m  =  obstacleGrid.length;
    int n = obstacleGrid[0].length;
    int[][] map = new int[m][n];
    if (obstacleGrid[0][0]==1){
      return 0;
    }else {
      map[0][0]=1;
    }
    for (int i =1;i<m;i++){
      if (obstacleGrid[i][0]==1){
        map[i][0]=0;
      }else {
        map[i][0]=map[i-1][0];
      }
    }
    for (int j =1;j<n;j++){
      if (obstacleGrid[0][j]==1){
        map[0][j]=0;
      }else {
        map[0][j]=map[0][j-1];
      }
    }
    for (int i =1;i<m;i++){
      for (int j=1;j<n;j++){
        if (obstacleGrid[i][j]==1){
          map[i][j]=0;
        }else {
          map[i][j]=map[i-1][j]+map[i][j-1];
        }
      }
    }
    return map[m-1][n-1];
  }


  public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    if (m==0||n==0){
      return 0;
    }
    int[][] map = new int[m][n];
    map[0][0]=grid[0][0];
    for (int i =1;i<m;i++){
      map[i][0]=grid[i][0]+map[i-1][0];
    }
    for (int j=1;j<n;j++){
      map[0][j]=grid[0][j]+map[0][j-1];
    }
    for (int i=1;i<m;i++){
      for (int j=1;j<n;j++){
        int min = Math.min(map[i-1][j],map[i][j-1]);
        map[i][j]=min+grid[i][j];
      }
    }
    return map[m-1][n-1];
  }

  @Test
  public void test(){
    int[][] obstacles = new int[][]{{0,0,0},{0,1,0},{0,0,0}};
    int result = uniquePathsWithObstacles(obstacles);
  }
}