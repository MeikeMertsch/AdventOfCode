(ns christmas.chr18.day17-test
 (:require [christmas.chr18.day17 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))

(comment 

(defn parse-int [thing]
	(Integer/parseInt thing))

(defn handle-row [my-string]
	(-> (re-seq #"\d+" my-string)
		(#(mapv parse-int %))))

(def file "resources/chr18/Input17")
(def fe-file "resources/chr18/Input17failingExample")
(def mini-file "resources/chr18/Input17failingMiniExample")
(def real-file "resources/chr18/Input17real")


(defn print-board [board]
	(chr/act-on-board board #(println (apply str %))))

(defn spit-board [file board]
	(chr/act-on-board board #(spit file (str (apply str %) "\n") :append true)))

;(expect "" file-board)
;(expect "" (print-board file-board))
;(expect-focused "" (spit-board "resources/chr18/Output17real" (chr/get-board real-file)))
(def file-board (chr/get-board file))
(def example-board (chr/get-board file))

(expect 7 (chr/fall-target [500 0] file-board))
(expect 7 (chr/fall-target [500 5] {:ground {[500 13] "#" [500 1] "#" [500 7] "#" }}))
(expect nil (chr/fall-target [500 0] {:ground {[200 6] "#" [300 7] "#" [400 8] "#" }}))
(expect 8 (chr/fall-target [500 0] {:ground {[200 6] "#" [500 8] "#" [400 7] "#" }}))
(expect 13 (chr/fall-target [502 2] file-board))

(expect {:ground {[500 1] "|" [500 2] "|" [500 3] "|"} :y2 4 :active #{}} 
	(chr/fall [500 0] {:ground {} :y2 4 :active #{[500 0]}}))

;(expect "" (print-board (chr/fall [500 0] file-board)))
;(expect "" (print-board (chr/fall [502 2] file-board)))
;(expect "" (print-board (chr/spread [500 6] file-board)))

(expect #{[500 6]} (:active (chr/fall [500 0] file-board)))
(expect #{[502 12]} (:active (chr/fall [502 2] (chr/get-board file #{[502 2]}))))
(expect #{} (:active (chr/fall [505 9] (chr/get-board file #{[505 9]}))))

(expect #{[500 5]} (:active (chr/spread [500 6] (chr/get-board file #{[500 6]}))))
(expect #{[502 6]} (:active (chr/spread [500 6] (update-in (chr/get-board file #{[500 6]}) [:ground] dissoc [501 6]))))
(expect #{[497 12][505 12]} (:active (chr/spread [502 12] (update-in (chr/get-board file #{[502 12]}) [:ground] dissoc [504 12] [498 12]))))
;(expect #{[497 12][505 12]} (print-board (chr/spread [502 12] (update-in (chr/get-board file #{[502 12]}) [:ground] dissoc [504 12] [498 12]))))
;(expect "" (chr/spread [500 6] (chr/get-board file #{[500 6]})))

(expect 498 (chr/left-target [500 4] file-board))
(expect nil (chr/left-target [502 8] file-board))

(expect 497 (chr/left-flow [502 12] 495 (update-in (chr/get-board file #{[502 12]}) [:ground] dissoc [504 12] [498 12])))
(expect nil (chr/left-flow [500 6] 495 file-board))

(expect 498 (chr/right-target [497 4] file-board))
(expect nil (chr/right-target [502 7] file-board))

(expect 505 (chr/right-flow [502 12] 506 (update-in (chr/get-board file #{[502 12]}) [:ground] dissoc [504 12] [498 12])))
(expect nil (chr/right-flow [500 6] 501 file-board))
(expect nil (chr/right-flow [499 6] 501 file-board))

(expect "#" (chr/has-bottom [499 6] file-board))
(expect nil (chr/has-bottom [502 2] file-board))

;(expect "" (print-board (chr/flood-field file)))

;(expect-focused 57 (chr/day17a file))
;(expect 0 (spit-board "resources/chr18/Output17real" (chr/flood-field real-file )))
;(expect 0 (chr/day17a real-file ))

;(expect-focused "" (print-board (chr/flood-field mini-file)))

;(expect 7 (chr/left-target [500 5] {:ground {[500 13] "#" [500 1] "#" [500 7] "#" }}))
;(expect nil (chr/left-target [500 0] {:ground {[200 6] "#" [300 7] "#" [400 8] "#" }}))
;(expect 8 (chr/left-target [500 0] {:ground {[200 6] "#" [500 8] "#" [400 7] "#" }}))


)














