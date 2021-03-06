package com.mmy.leetcode.medium;

import java.util.ArrayDeque;
import java.util.ArrayList;
import org.junit.Test;

/**
 * @author: mmy
 * @date: 2018/08/10
 * @description:
 */
public class Medium9 {

  public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    int m = obstacleGrid.length;
    int n = obstacleGrid[0].length;
    int[][] map = new int[m][n];
    if (obstacleGrid[0][0] == 1) {
      return 0;
    } else {
      map[0][0] = 1;
    }
    for (int i = 1; i < m; i++) {
      if (obstacleGrid[i][0] == 1) {
        map[i][0] = 0;
      } else {
        map[i][0] = map[i - 1][0];
      }
    }
    for (int j = 1; j < n; j++) {
      if (obstacleGrid[0][j] == 1) {
        map[0][j] = 0;
      } else {
        map[0][j] = map[0][j - 1];
      }
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (obstacleGrid[i][j] == 1) {
          map[i][j] = 0;
        } else {
          map[i][j] = map[i - 1][j] + map[i][j - 1];
        }
      }
    }
    return map[m - 1][n - 1];
  }


  public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    if (m == 0 || n == 0) {
      return 0;
    }
    int[][] map = new int[m][n];
    map[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) {
      map[i][0] = grid[i][0] + map[i - 1][0];
    }
    for (int j = 1; j < n; j++) {
      map[0][j] = grid[0][j] + map[0][j - 1];
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        int min = Math.min(map[i - 1][j], map[i][j - 1]);
        map[i][j] = min + grid[i][j];
      }
    }
    return map[m - 1][n - 1];
  }


  /**
   * Given an absolute path for a file (Unix-style), simplify it.
   *
   * For example, path = "/home/", => "/home" path = "/a/./b/../../c/", => "/c"
   *
   * Corner Cases:
   *
   * Did you consider the case where path = "/../"? In this case, you should return "/". Another
   * corner case is the path might contain multiple slashes '/' together, such as "/home//foo/". In
   * this case, you should ignore redundant slashes and return "/home/foo"
   *
   *
   * 自己的，，，，13ms
   */
  public String simplifyPath(String path) {
    String[] map = path.split("/");
    if (map.length == 0) {
      return "/";
    }
    String[] step = new String[map.length];
    step[0] = "/";
    for (int i = 1; i < map.length; i++) {
      if (map[i].equals("") || map[i].equals(".")) {
        step[i] = step[i - 1];
      } else if (map[i].equals("..")) {
        if (i < 2) {
          step[i] = "/";
        } else {
          int index = step[i - 1].lastIndexOf("/");
          if (index != 0) {
            step[i] = step[i - 1].substring(0, index);
          } else {
            step[i] = step[i - 1].substring(0, 1);
          }
        }
      } else {
        if (!step[i - 1].equals("/")) {
          step[i] = step[i - 1] + "/" + map[i];
        } else {
          step[i] = step[i - 1] + map[i];
        }
      }
    }

    return step[map.length - 1];
  }

  /**
   * 用队列做的，感觉不错，6ms
   */
  public String simplifyPath2(String path) {
    ArrayDeque<String> queue = new ArrayDeque<>();
    path = path.substring(1, path.length());
    String[] ar = path.split("/");
    for (int i = 0; i < ar.length; i++) {
      if (ar[i].equals("..")) {
        if (!queue.isEmpty()) {
          queue.removeLast();
        }
      } else if (ar[i].equals(".")) {
        continue;
      } else if (ar[i].equals("")) {
        continue;
      } else {
        queue.add(ar[i]);
      }

    }
    StringBuffer sb = new StringBuffer("/");
    while (!queue.isEmpty()) {
      sb.append(queue.poll());
      if (!queue.isEmpty()) {
        sb.append("/");
      }
    }
    return sb.toString();
  }

  /**
   * 指针偏移做的，3ms
   */
  public String simplifyPath3(String path) {
    int pathIndex = 0;
    ArrayList<String> result = new ArrayList<>();
    while (pathIndex < path.length()) {
      while (pathIndex < path.length() && path.charAt(pathIndex) == '/') {
        pathIndex++;
      }
      int nameEndIndex = pathIndex + 1;
      while (nameEndIndex < path.length() && path.charAt(nameEndIndex) != '/') {
        nameEndIndex++;
      }
      if (pathIndex == path.length()) {
        break;
      }
      String temporary = path.substring(pathIndex, nameEndIndex);
      if (temporary.equals("..")) {
        if (result.size() > 0) {
          result.remove(result.size() - 1);
        }
      } else if (!temporary.equals(".")) {
        result.add(temporary);
      }

      pathIndex = nameEndIndex;
    }
    StringBuilder finalResult = new StringBuilder();
    for (String temporary : result) {
      finalResult.append("/");
      finalResult.append(temporary);
    }
    if (finalResult.length() == 0) {
      finalResult.append("/");
    }
    return finalResult.toString();
  }

  /**
   * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
   * use m+n place
   */
  public void setZeroes(int[][] matrix) {
    if (matrix.length==0){
      return;
    }
    boolean[] rowZero = new boolean[matrix.length];
    boolean[] columnZero = new boolean[matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j]==0){
          rowZero[i]=true;
          columnZero[j]=true;
        }
      }
    }
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == 0) {
          if (columnZero[j]) {
            for (int m = 0; m < matrix.length; m++) {
              matrix[m][j] = 0;
            }
          }
          if (rowZero[i]) {
            for (int n = 0; n < matrix[0].length; n++) {
              matrix[i][n] = 0;
            }
          }

        }
      }
    }
  }


  /**
   * use constant space
   *
   * 意思是先把数组的左与上边界置0
   * 然后根据边界来确定内部的值是否为0；
   * fr与fc为控制边界条件
   *
   * 牛
   * @param matrix
   */
  public void setZeroes2(int[][] matrix) {
    boolean fr = false,fc = false;
    for(int i = 0; i < matrix.length; i++) {
      for(int j = 0; j < matrix[0].length; j++) {
        if(matrix[i][j] == 0) {
          if(i == 0) fr = true;
          if(j == 0) fc = true;
          matrix[0][j] = 0;
          matrix[i][0] = 0;
        }
      }
    }
    for(int i = 1; i < matrix.length; i++) {
      for(int j = 1; j < matrix[0].length; j++) {
        if(matrix[i][0] == 0 || matrix[0][j] == 0) {
          matrix[i][j] = 0;
        }
      }
    }
    if(fr) {
      for(int j = 0; j < matrix[0].length; j++) {
        matrix[0][j] = 0;
      }
    }
    if(fc) {
      for(int i = 0; i < matrix.length; i++) {
        matrix[i][0] = 0;
      }
    }
  }

  /**
   * 2分法做
   * @param matrix
   * @param target
   * @return
   */
  public boolean searchMatrix(int[][] matrix, int target) {
    int rightTopX =0;
    int rightTopY = matrix[0].length-1;
    while (rightTopX<matrix.length&&rightTopY>=0){
      int val  = matrix[rightTopX][rightTopY];
      if (target>val){
        rightTopX++;
      }else if (target<val){
        rightTopY--;
      }else {
        return true;
      }
    }
    return false;
  }

  /**
   * 另一种2分法，很快
   * @param matrix
   * @param target
   * @return
   */
  public boolean searchMatrix2(int[][] matrix, int target) {
    if(matrix==null || matrix.length==0 || matrix[0].length ==0) return false;
    int l =0,r=matrix.length*matrix[0].length-1;
    int col=matrix[0].length;
    while(l+1<r){
      int mid = l +(r-l)/2;
      if(matrix[mid/col][mid%col]>target){
        r = mid;
      }else{
        l = mid;
      }
    }

    return matrix[l/col][l%col]==target || matrix[r/col][r%col]==target;


  }





  @Test
  public void test() {
//      int[][] obstacles = new int[][]{{0,0,0},{0,1,0},{0,0,0}};
//      int result = uniquePathsWithObstacles(obstacles);

    String test1 = "/home/../../..";
    String test2 = "/a/.//b/../../c/";
    String test3 = "///";
    String result1 = simplifyPath(test1);
    String result2 = simplifyPath(test2);
    String result3 = simplifyPath(test3);
  }
}
