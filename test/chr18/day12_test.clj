(ns christmas.day12-test
 (:require [christmas.day12 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))
(comment
(defn dot [string]
	(str/replace string "." " "))

(defn get-file [file-name]
	(->> (slurp file-name)
			   dot
			   str/split-lines
			   (map #(str/split % (re-pattern " => ")))
			   (mapcat #(vector (seq (first %)) (first (last %))))
			   (apply assoc {})))

(def file (get-file "resources/day11-15/Input12"))
(def realFile (get-file "resources/day11-15/Input12real"))

(def init (dot "#..#.#..##......###...###"))
(def initReal (dot "##.###.......#..#.##..#####...#...#######....##.##.##.##..#.#.##########...##.##..##.##...####..####"))

;(expect "Just the file" file)

(expect (seq (dot "...#..#.#..##......###...###...")) (chr/init-pots init))
(expect (seq (dot ".#...#....#.....#..#..#..#.")) (chr/re-pot init file 1))

(expect (seq (dot ".#...#....#.....#..#..#..#.")) (first (chr/day12 init file 1)))

(expect [(seq (dot "..##..##...##....#..#..#..##.")) 1] (chr/day12 init file 2))
(expect [(seq (dot "..#.#...#..#.#....#..#..#...#..")) 2] (chr/day12 init file 3))
(expect [(seq (dot ".........#.#..#...#.##....##..##..##..##.....")) 9] (chr/day12 init file 10))
(expect [(seq (dot "..................#....##....#####...#######....#.#..##..........")) 19] 
	(chr/day12 init file 20))

(expect 325 (chr/count-pots init file 20))
(expect 1430 (chr/count-pots initReal realFile 20))

(expect 3217 (chr/count-pots initReal realFile 120))

;------- here I looked at my output and realised I can do it with easy math. I did not code the rest
(def row112 "                                                                                                                                                                      #    #    #        #    #                #     #    #    #    #        #     #         #     #     #    #     #     #      #    #     #    #       #        ")
(def row113 "                                                                                                                                                                        #    #    #        #    #                #     #    #    #    #        #     #         #     #     #    #     #     #      #    #     #    #       #        ")
(def row114 "                                                                                                                                                                         #    #    #        #    #                #     #    #    #    #        #     #         #     #     #    #     #     #      #    #     #    #       #        ")
(def row21  "                                  #    #      #   #  # #    #    #    #   #   ###  # ## #     #     #      #    #     #    #       #        ")

(expect 322 (count row112))
(expect 166 (count (take-while #(= \space %) row112)))

(expect 3010 (->> (zipmap (range (- 0 111) (+ 111 1000)) row112) (remove #(= \space (last %))) (map first) (apply + )))
(expect 3010 (chr/do-math row112 111))
(expect 3033 (chr/do-math row113 112))
(expect 1430 (->> (zipmap (range (- 0 20) (+ 20 1000)) row21) (remove #(= \space (last %))) (map first) (apply + )))
(expect 1430 (chr/do-math row21 20))
(expect 1150000000457 (+ 3010 (* 23 (- 50000000000 111))))
;(expect "result" (- 3033 3010))

)