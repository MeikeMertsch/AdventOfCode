(ns christmas.chr16.day13-test
  (:require [christmas.chr16.day13 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(def t-input 10)
(def t-goal [7 4])

(def input 1362)
(def goal [31 39])

(comment
(expect 11 (chr/walk t-input t-goal))
;(expect 82 (chr/walk input goal))

(expect 138 (chr/walk2 input goal)) ;; 173 too high ;134 too low

(expect \1 (first (Integer/toBinaryString 3)))

(expect false (chr/valid? #{[1 5] [2 5]} [2 5]))
(expect false (chr/valid? #{[1 5] [2 5]} [-2 5]))
(expect true (chr/valid? #{[1 5] [2 5]} [3 5]))

(expect #{[0 1][1 0][2 1][1 2]} (set (chr/neighbors [1 1])))

(expect #{[0 1][1 0][2 1][1 2]} (set (chr/find-possibilities {:actual [[1 1]] :blocked #{} :fav input :goal goal})))


(expect true (chr/way? 10 [1 1]))
(expect false (chr/way? 10 [4 0]))
(expect false (chr/way? 10 [9 0]))
(expect true (chr/way? 10 [6 6]))
)
