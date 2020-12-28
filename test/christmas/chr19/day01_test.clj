(ns christmas.chr19.day01-test
  (:require [christmas.chr19.day01 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
  (def realfile "resources/chr19/day01real")
(def file "resources/chr19/day01")

(expect (+ 2 2 654 33583) (chr/day01 file))
(expect 3352674 (chr/day01 realfile))


(expect (+ 2 2 966 50346) (chr/day01b file))
(expect 5026151 (chr/day01b realfile)))