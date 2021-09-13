package com.github.md.analysis.db;

/**
 * <p> @Date : 2021/9/2 </p>
 * <p> @Project : MD</p>
 *
 * <p> @author konbluesky </p>
 */
class SnowFlakeTest {

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(1, 1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(snowFlake.nextId());
        }

        System.out.println(System.currentTimeMillis() - start);
    }
}