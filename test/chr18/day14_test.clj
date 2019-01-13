(ns christmas.day14-test
 (:require [christmas.day14 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))
(comment
(def init "37")

(expect ["371010" 4 3] (chr/tick "3710" 0 1))
(expect ["371010124" 4 8] (chr/tick "37101012" 0 6))

(expect "5158916779" (chr/day14 9))
(expect "0124515891" (chr/day14 5))
(expect "9251071085" (chr/day14 18))
(expect "5941429882" (chr/day14 2018))
;(expect "5941429882" (chr/day14 293801)) ;35071 msecs

(expect 9 (chr/day14b-part2 [5 1 5 8 9]))
(expect 5 (chr/day14b-part2 [0 1 2 4 5]))
(expect 18 (chr/day14b-part2 [9 2 5 1 0]))
(expect 2018 (chr/day14b-part2 [5 9 4 1 4]))
;
;(expect 2018 (chr/day14b-part2 [2 9 3 8 0 1])) ;40100 msecs

(expect [[3 7 1 0 1 0] 4 3] (chr/tick-2 [3 7 1 0] 0 1))
(expect [[3 7 1 0 1 0 1 2 4] 4 8] (chr/tick-2 [3 7 1 0 1 0 1 2] 0 6))

(expect [5 1 5 8 9 1 6 7 7 9] (chr/day14-part1 9))
(expect [0 1 2 4 5 1 5 8 9 1]  (chr/day14-part1 5))
(expect [9 2 5 1 0 7 1 0 8 5] (chr/day14-part1 18))
(expect [5 9 4 1 4 2 9 8 8 2] (chr/day14-part1 2018))
(expect [3 1 4 7 5 7 4 1 0 7] (chr/day14-part1 293801))

(expect 5 (chr/last-index [0 1 2 3] [9 4 5 4 3 0 1 2 3 4]))
(expect 4 (chr/last-index [0 1 2] [9 4 4 3 0 1 2]))
)