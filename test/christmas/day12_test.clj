(ns christmas.day12-test
 (:require [christmas.day12 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))


(def file (->> (slurp "resources/Input12")
	 			str/split-lines
	 			(map #(str/split % (re-pattern " => ")))
	 			(map #(map seq %))
	 			(apply concat)
	 			(apply assoc {})))

(def realFile (->> (slurp "resources/Input12real")
	 			str/split-lines
	 			(map #(str/split % (re-pattern " => ")))
	 			(apply concat)
	 			(apply assoc {})))



(def init "#..#.#..##......###...###")
(def initReal "##.###.......#..#.##..#####...#...#######....##.##.##.##..#.#.##########...##.##..##.##...####..####")

;(expect "Just the file" file)


(expect "...#..#.#..##......###...###..." (apply str (chr/init-pots init)))
(expect ".#...#....#.....#..#..#..#." (apply str (chr/re-pot init file)))

(expect ".#...#....#.....#..#..#..#." (first (chr/day12 init file 1)))

(expect ["..##..##...##....#..#..#..##." 1] (chr/day12 init file 2))
(expect ["..#.#...#..#.#....#..#..#...#.." 2] (chr/day12 init file 3))
(expect [".........#.#..#...#.##....##..##..##..##....." 9] (chr/day12 init file 10))
(expect ["..................#....##....#####...#######....#.#..##.........." 19] 
	(chr/day12 init file 20))

(expect 325 (chr/count-pots init file 20))
(expect 1430 (chr/count-pots initReal realFile 20))

;(expect-focused 325 (chr/count-pots initReal realFile 500)) ;50000000000))