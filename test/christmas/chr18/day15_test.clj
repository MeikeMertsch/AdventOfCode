(ns christmas.chr18.day15-test
 (:require [christmas.chr18.day15 :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))

(defn get-file [file-name]
	(->> (slurp file-name)
		 str/split-lines))

(def file (get-file "resources/chr18/Input15"))
(def file-c0 (get-file "resources/chr18/Input15c0"))
(def file-d (get-file "resources/chr18/Input15d"))
(def file-e (get-file "resources/chr18/Input15e"))
(def file-f (get-file "resources/chr18/Input15f"))
(def file-c1 (get-file "resources/chr18/Input15c1"))
(def fileb (get-file "resources/chr18/Input15b"))
(def realFile (get-file "resources/chr18/Input15real"))


(expect [[1 1]] (chr/input-row 1 (second file) \E))
(expect #{[1 1]} (chr/get-elves file))
(expect #{[4 1][2 3][5 3]} (chr/get-goblins file))
(expect #{[2 1][3 1][5 1][1 2][2 2][3 2][5 2][1 3][3 3]} (chr/get-free file))

;.  .
; E..
;   .G
(def free-ex #{[0 0][3 0][2 1][3 1][3 2]})
(def board {:elves (chr/dec-creatures (chr/get-elves file) 200) :goblins (chr/dec-creatures (chr/get-goblins file) 200) :free (chr/get-free file)})

(expect #{[0 1][2 1][1 0][1 2]} (into #{} (chr/sorted-neighbours [1 1])))
(expect #{[2 1][4 1][3 0][3 2]} (into #{} (chr/sorted-neighbours [3 1])))
(expect #{[2 1]} (into #{} (chr/next-step [[1 1]] free-ex #{[4 2]})))
(expect #{[3 0][3 2]} (into #{} (chr/next-step [[1 1][2 1][3 1]] free-ex #{[4 2]})))
(expect #{[2 1][3 0][3 2]} (into #{} (chr/next-step [[3 1]] free-ex #{[4 2]})))

(expect [[[1 1] [2 1]]] (chr/next-steps [[1 1]] free-ex #{[4 2]}))
(expect [] (chr/next-steps [[1 1][2 1][3 1][3 0]] free-ex #{[4 2]}))
(expect [[[1 1][2 1][3 1][3 2][4 2]]] (chr/next-steps [[1 1][2 1][3 1][3 2]] free-ex #{[4 2]}))
(expect #{[[1 1][2 1][3 1][3 2]]
		  [[1 1][2 1][3 1][3 0]]} (into #{} (chr/next-steps [[1 1][2 1][3 1]] free-ex #{[4 2]})))

(expect #{[[3 1] [2 1] [1 1]] 
				  [[3 1] [2 1] [2 2]] 
				  [[3 1] [4 1] [5 1]] 
				  [[3 1] [4 1] [4 2]] 
				  [[3 1] [3 2] [3 3]]} 
				  (into #{} 
				  (chr/reduced-next-steps #{[1 1][2 1][4 1][5 1]
		  									[1 2][2 2][3 2][4 2][5 2]
		  									[1 3][2 3][3 3][4 3][5 3]
		  									[1 4][2 4][5 4]
		  									[1 5][2 5][4 5][5 5]} 
		  								  #{[3 5]} 
		  								  [[[3 1] [2 1]] [[3 1] [4 1]] [[3 1] [3 2]]])))


(expect [[[1 1][2 1][3 1][3 2][4 2]]] (chr/ways-with-enemies [[[1 1][2 1][3 1][3 2][4 2]]
											[[1 1][2 1][3 1][3 2]]
											[[1 1][2 1][3 1][3 0]]] #{[4 2]}))

(expect [2 1] (chr/next-pos [1 1] free-ex #{[4 2]}))
(expect [2 1] (chr/next-pos [1 1] (chr/get-free file) (chr/get-goblins file)))
;(expect [1 1] (chr/next-pos [2 1] (chr/get-free fileb) (chr/get-goblins fileb)))
(expect [3 1] (chr/next-pos [3 2] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos [3 3] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos [2 2] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos [1 3] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos [3 1] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos [3 3] (chr/get-free fileb) (chr/get-goblins fileb)))

(expect {[1 1] 200} (chr/dec-creatures (chr/get-elves file) 200))

(expect {:elves {[2 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [4 1] 200}, 
		:free #{[2 2] [3 3] [1 1] [5 2] [1 3] [5 1] [3 1] [1 2] [3 2]}}
	(chr/tick [1 1] board))

(expect {:elves {[1 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [3 1] 200}, 
		:free #{[2 2] [3 3] [2 1] [5 2] [1 3] [5 1] [4 1] [1 2] [3 2]}}
	(chr/tick [4 1] board))

(expect {:elves {[2 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [4 1] 200}, 
		:free #{[2 2] [3 3] [1 1] [5 2] [1 3] [5 1] [3 1] [1 2] [3 2]}}
	(chr/take-turn [1 1] :elves :goblins board))

(expect {:elves {[1 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [3 1] 200}, 
		:free #{[2 2] [3 3] [2 1] [5 2] [1 3] [5 1] [4 1] [1 2] [3 2]}}
	(chr/take-turn [4 1] :goblins :elves board))

(expect {:elves {[2 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [4 1] 200}, 
		:free #{[2 2] [3 3] [1 1] [5 2] [1 3] [5 1] [3 1] [1 2] [3 2]}} 
	(chr/move [1 1] [2 1] :elves board))

(expect {:elves {[1 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [3 1] 200}, 
		:free #{[2 2] [3 3] [2 1] [5 2] [1 3] [5 1] [4 1] [1 2] [3 2]}} 
	(chr/move [4 1] [3 1] :goblins board))

(expect {:elves {[1 1] 200}, 
		:goblins {[2 3] 200, [5 3] 200, [4 1] 197}, 
		:free #{[2 2] [3 3] [2 1] [5 2] [1 3] [5 1] [3 1] [1 2] [3 2]}} 
	(chr/attack [4 1] :goblins board))

(expect [4 1]
	(chr/victim [3 1] :goblins board))

(expect nil
	(chr/victim [2 1] :goblins board))

(expect [3 2]
	(chr/victim [3 1] :goblins {:goblins {[3 0] 200 [3 2] 2}}))

(expect board (chr/create-board file))

(expect {:elves {[5 4] 197, [4 2] 197}, 
		 :goblins {[5 3] 197, [5 2] 197, [3 1] 200, [3 3] 200}, 
		 :free #{[2 2] [2 5] [1 1] [3 4] [4 1] [1 4] [1 3] [1 5] [5 1] [5 5] [2 4] [4 5] [2 1] [1 2] [3 5] [3 2]}}
	(chr/play-round (chr/create-board file-c0)))

;(expect-focused [4 2] (chr/choose-target [2 1] (chr/get-free file-c0) (chr/get-elves file-c0)))
(expect [3 1] (chr/choose-target [4 2] (chr/get-free file-c0) (into #{} (filter (chr/get-free file-c0) (chr/sorted-neighbours [2 1])))))


(expect {:elves [2 1] :goblins {[2 1] 200} :free #{[1 1][4 3]}} (chr/remove-bodies [4 3] :goblins {:elves [2 1] :goblins {[2 1] 200 [4 3] 0} :free #{[1 1]}}))
(expect {:elves [2 1] :goblins {[2 1] 200} :free #{[1 1][4 3]}} (chr/remove-bodies [4 3] :goblins {:elves [2 1] :goblins {[2 1] 200 [4 3] -1} :free #{[1 1]}}))
(expect {:elves [2 1] :goblins {[2 1] 200 [4 3] 1} :free #{[1 1]}} (chr/remove-bodies [4 3] :goblins {:elves [2 1] :goblins {[2 1] 200 [4 3] 1} :free #{[1 1]}}))

;(expect true (chr/will-move [2 1] (chr/get-free file-c1) (chr/get-elves file-c1)))
(expect false (chr/will-move [5 2] (chr/get-free file-c0) (chr/get-elves file-c0)))
(expect false (chr/will-move [5 3] (chr/get-free file-c0) (chr/get-elves file-c0)))


(expect true (chr/will-move [3 3] (chr/get-free file-c1) (chr/get-elves file-c1)))

(expect [2 1] (chr/next-pos-2 [1 1] free-ex #{[4 2]}))
(expect [2 1] (chr/next-pos-2 [1 1] (chr/get-free file) (chr/get-goblins file)))
(expect [3 1] (chr/next-pos-2 [3 2] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos-2 [3 3] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos-2 [2 2] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos-2 [1 3] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos-2 [3 1] (chr/get-free file) (chr/get-goblins file)))
(expect nil (chr/next-pos-2 [3 3] (chr/get-free fileb) (chr/get-goblins fileb)))

;(expect-focused [3 1] (chr/next-pos-2 [2 1] (chr/get-free file-c0) (chr/get-elves file-c0)))
(expect [5 4] (chr/choose-target [3 3] (chr/get-free file-c1) (chr/get-elves file-c1)))
(expect [4 2] (chr/choose-target [2 1] (chr/get-free file-c0) (chr/get-elves file-c0)))
(expect [4 2] (chr/choose-target [4 1] (chr/get-free file-c0) (chr/get-elves file-c0)))
;(expect [5 4] (chr/choose-target [3 3] (chr/get-free file-c1) (chr/get-elves file-c1)))

(expect nil 
	(chr/choose-target [1 1] 
		#{[2 5] [3 3] [3 4] [4 2] [4 1] [5 2] [1 4] [1 3] [1 5] [5 1] [5 5] [2 4] [4 5] [3 1] [2 1] [1 2] [3 2]} 
		#{[5 4]}))

(expect nil 
	(chr/choose-target nil 
		#{[2 5] [3 3] [3 4] [4 2] [4 1] [5 2] [1 4] [1 3] [1 5] [5 1] [5 5] [2 4] [4 5] [3 1] [2 1] [1 2] [3 2]} 
		#{[5 4]}))


;(expect-focused {:elves {[5 4] 119}, 
;		:goblins {[1 1] 200, [2 2] 131, [5 2] 119 [4 5] 200}, 
;		:free #{[2 1][3 1][4 1][5 1][1 2][3 2][4 2][5 2][1 3][3 3][1 4][2 4][3 4][1 5][2 5][3 5][5 5]}}
;	(chr/take-turn-2 [1 1] :goblins :elves {:elves {[5 4] 122}, 
;										   :goblins {[1 1] 200, [2 2] 131, [5 2] 122 [3 5] 200}, 
;										   :free #{[2 1][3 1][4 1][5 1][1 2][3 2][4 2][5 2][1 3][3 3][1 4][2 4][3 4][1 5][2 5][4 5][5 5]}}))

(expect {:elves {[5 4] 119}, 
		:goblins {[1 1] 200, [2 2] 131, [5 3] 119 [4 5] 200}, 
		:free #{[2 1][3 1][4 1][5 1][1 2][3 2][4 2][5 2][1 3][3 3][1 4][2 4][3 4][1 5][2 5][3 5][5 5]}}
	(chr/play-round {:elves {[5 4] 122}, 
					 :goblins {[1 1] 200, [2 2] 131, [5 3] 122 [3 5] 200}, 
					 :free #{[2 1][3 1][4 1][5 1][1 2][3 2][4 2][5 2][1 3][3 3][1 4][2 4][3 4][1 5][2 5][4 5][5 5]}}))

;:elves {[4 2] 2}
;:goblins {[3 4] 200, [5 3] 2, [2 1] 200}
;:free #{[2 2] [2 5] [3 3] [5 4] [1 1] [4 1] [5 2] [1 4] [1 3] [1 5] [5 1] [5 5] [2 4] [4 5] [3 1] [1 2] [3 5] [3 2]}

; 12345           #12345   
;1G....#   G(200) 1G....#   G(200)
;2.G...#   G(131) 2.G...#   G(131)
;3.#.#G#   G(119) 3.#.#G#   G(122)
;4...#E#   E(119) 4...#E#   E(122)
;5...G.#   G(200) 5..G..#   G(200)



(expect 800 (chr/hp board))

;(expect-focused 27730 (chr/play file-c0))
;(expect 18740 (chr/play file-d))
;(expect 28944 (chr/play file-e))
;(expect-focused 28944 (chr/play realFile))






