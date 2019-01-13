(ns christmas.chr18.day10-test
 (:require [christmas.chr18.day10 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))
(comment
(defn parse-int [a-str]
	(Integer/parseInt (str/trim a-str)))

(defn getFile [fileName]
	(->> (slurp fileName)
		 (clojure.string/split-lines)
		 (map #(str/replace % (re-pattern "position=<") ""))
		 (map #(str/replace % (re-pattern ">") ""))
		 (map str/trim)
		 (map #(str/split % (re-pattern " velocity=<")))
		 (map #(map (comp (fn [y] (map parse-int y))(fn [x] (str/split x (re-pattern ", "))) str/trim) %))
		 (map #(apply hash-map %))
		 (apply merge)
		 ))
		 

(def file (getFile "resources/day06-10/Input10"))
;(def realFile (getFile "resources/Input10real"))

(expect [3 2] (chr/move-star 4 [[7 6][-1 -1]]))

;(expect '([4 3] [0 5] [8 6] [1 3] [3 3] [9 0] [0 6] [4 1] [0 2] [0 1] [0 4] [0 0] [8 4] [4 4] [7 7] [8 1] [2 3] [4 5] [4 7] [8 5] [8 3] [0 3] [4 2] [9 7] [4 0] [8 2] [0 7] [7 0] [4 6] [8 0] [8 7]) 
	;(chr/find-candidate file))

;(expect "" (chr/find-candidate file))

;(expect nil (chr/day10 file))

;(expect {[3 1] "X", [4 5] "X"} (chr/star-pixels [[3 1] [4 5]]))

;(expect-focused nil (chr/day10 file))

;(expect-focused nil ( chr/size (chr/find-candidate realFile)))
;(expect-focused nil ( chr/day10 realFile))

(let [stars {[0 0] "X" [3 1] "X", [4 5] "X"}
	  board (chr/board (keys stars))
	  big-board (chr/board (keys file))]
;(expect "X" (chr/paint-pixel stars 3 1 ))
;(expect "." (chr/paint-pixel stars 3 5))

;(expect nil (chr/paint-row (ffirst board) (first (last board)) stars 3))
;(expect nil (chr/paint-row (ffirst board) (first (last board)) stars 2))

;(expect nil (sort-by last (keys file)))
;(expect nil (chr/paint-row (ffirst big-board) (first (last big-board)) (chr/star-pixels (keys file)) -4))
;(expect nil (chr/paint-row (ffirst big-board) (first (last big-board)) (chr/star-pixels (keys file)) -3))

;(expect nil (chr/paint-board (keys stars)))


))
