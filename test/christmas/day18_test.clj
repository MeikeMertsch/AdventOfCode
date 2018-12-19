(ns christmas.day18-test
 (:require [christmas.day18 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))


(defn get-file [file]
	(->> (slurp file)
		 (str/split-lines)))

(def init-file (get-file "resources/day16-20/Input18"))
(def m10-file (get-file "resources/day16-20/Input18b"))
(def m01-file (get-file "resources/day16-20/Input18c"))
(def m02-file (get-file "resources/day16-20/Input18d"))
(def real-file (get-file "resources/day16-20/Input18real"))

(expect {[3 0] "|" [6 0] "#"} (chr/init ["...|..#."]))

(expect 31 (count (chr/get-type "#" (chr/init m10-file))))
(expect 37 (count (chr/get-type "|" (chr/init m10-file))))

(expect {[6 0] "#"} (chr/get-type "#" (chr/init ["...|..#."])))

(expect [[3 1] "|"] (chr/tick-spot [3 1] {[2 1] "|" [4 1] "|" [2 0] "|" [3 0] "#"}))
(expect nil (chr/tick-spot [3 1] {[2 1] "#" [4 1] "|" [2 0] "|" [3 0] "#"}))

(expect [[3 1] "#"] (chr/tick-spot [3 1] {[3 1] "|" [4 1] "#" [2 0] "#" [3 0] "#" [4 2] "|"}))
(expect [[3 1] "|"] (chr/tick-spot [3 1] {[2 1] "#" [3 1] "|" [2 0] "|" [3 0] "#"}))

(expect [[3 1] "#"] (chr/tick-spot [3 1] {[3 1] "#" [4 1] "#" [2 0] "#" [3 0] "#" [4 2] "|"}))
(expect nil (chr/tick-spot [3 1] {[2 1] "#" [3 1] "#" [2 0] "#" [3 0] "#"}))
(expect nil (chr/tick-spot [3 1] {[2 1] "|" [3 1] "#" [2 0] "|" }))
(expect nil (chr/tick-spot [3 1] {}))

(expect (chr/init m01-file) (chr/tick-board 10 (chr/init init-file)))

(defn act-on-board [area size func]
	(map (fn [row] 
			(func (map #(get area [% row] " ") 
						(range size))))
		 (range size)))

(defn print-board [board size]
	(act-on-board board size #(println (apply str %))))


;(expect (chr/init m10-file) (print-board (chr/change-landscape init-file 10) 10))
(expect (chr/init m01-file) (chr/change-landscape init-file 1))
(expect (chr/init m02-file) (chr/change-landscape init-file 2))
(expect (chr/init m10-file) (chr/change-landscape init-file 10))

(expect [37 31 (* 37 31)] (chr/day18a 10 init-file))
;(expect [915 526 481290] (chr/day18a 10 real-file))

;(expect [572 316 180752] (chr/day18b real-file))
(expect 0 (rem (- 1000000000 1000) 28))

;1000th it [572 316 180752] 587 323
;repeat is 28




