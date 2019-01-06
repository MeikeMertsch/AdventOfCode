(ns christmas.day22b-test
 (:require [christmas.day22b :as chr]
 		   [clojure.string :as str]
  		   [expectations :refer :all]))

(comment
(def ex-depth 510)
(def ex-columns 10)
(def ex-rows 10)
(def ex-target [10 10])

(def depth 7305)
(def columns 13)
(def rows 734)
(def target [13 734])

(expect 510 (chr/erosion-level ex-depth 0))
(expect 17317 (chr/erosion-level ex-depth 16807))
(expect 8415 (chr/erosion-level ex-depth 48271))
(expect 1805 (chr/erosion-level ex-depth 145722555))

(expect [510 0] (chr/pixel ex-depth 0))
(expect [17317 1] (chr/pixel ex-depth 16807))
(expect [8415 0] (chr/pixel ex-depth 48271))
(expect [1805 2] (chr/pixel ex-depth 145722555))

(expect [0 1 0 2 1 0 2 0 2 1 0] (map last (chr/row-0 ex-depth ex-columns)))
(expect [0 0 0 1 1 1 2 2 0 0 0] (map last (map (partial chr/px-0 ex-depth) (take 11 (range)))))

(expect [1805 2] (chr/next-px ex-depth [17317 1] [8415 0]))

(expect [0 2 1 2 1 2 2 2 0 0 2] (map last (chr/next-row ex-target ex-depth (chr/row-0 ex-depth ex-columns) 1)))

(expect 114 (chr/day22a ex-depth ex-rows ex-columns))
(expect 10204 (chr/day22a depth rows columns))


(defn act-in-cave [cave func]
	(map func (map (partial map {0 "." 1 "=" 2 "|"}) cave)))

(defn act-on-board [area [columns  rows] func]
	(map (fn [row] 
			(func (map #({0 "." 1 "=" 2 "|"} (get area [% row] " ")) 
						(range columns))))
		 (range rows)))

(defn print-board [board size]
	(act-on-board board size #(println (apply str %))))

(defn output-board [board size]
	(act-on-board board size #(spit "resources/day16-20/Output20b2" (str (apply str %) "\n") :append true)))

(defn print-cave [cave]
	(act-in-cave cave #(println (apply str %))))

(defn output-cave [cave]
	(act-in-cave cave #(spit "resources/day16-20/Output20b" (apply str (concat "\n" %)) :append true)))

;(expect "" (output-board (chr/transform-to-map ex-depth (+ 6 ex-rows) (+ 6 ex-columns) ex-target) [(+ 6 ex-columns) (+ 6 ex-rows)]))
;(expect "" (output-board (chr/transform-to-map depth (+ 8 rows) (+ 8 columns))))

(def around 8)
(def t-cave (chr/transform-to-map ex-depth (+ around ex-rows) (+ around ex-columns) ex-target))
(def mini-cave (chr/transform-to-map ex-depth 5 5 [3 4]))
(def cave (chr/transform-to-map depth (+ around rows) (+ around columns) target))
(def t-maxima [(+ around ex-columns) (+ around ex-rows)])
(def maxima [(+ around columns) (+ around rows)])

(def t-status-0 {:candidates {[0 0 1] 0} :perms {} :maxima t-maxima :cave t-cave})
(def m-status-0 {:candidates {[0 0 1] 0} :perms {} :maxima [4 4] :cave mini-cave})
(def status-0 {:candidates {[0 0 1] 0} :perms {} :maxima maxima :cave cave})
(def t-status-1 {:candidates {[0 1 1] 1 [0 0 2] 7} :perms {[0 0 1] 0} :maxima t-maxima :cave t-cave })
(def t-status-2 {:candidates {[1 1 1] 2 [0 2 1] 2 [0 1 2] 8 [0 0 2] 7} :perms {[0 0 1] 0 [0 1 1] 1} :maxima t-maxima :cave t-cave })

(expect {[0 1 1] 1 [0 0 2] 7} (chr/valid-neighbours t-status-0 [[0 0 1] 0]))

(expect t-status-1 (chr/tick t-status-0))
(expect t-status-2 (chr/tick t-status-1))

;(expect 45  (chr/day22b t-status-0 ex-target))
;(expect 1004  (chr/day22b status-0 target))
;(expect-focused 21 (chr/day22b m-status-0 [3 4]))
)