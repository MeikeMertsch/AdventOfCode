(ns christmas.chr16.day12-test
  (:require [christmas.chr16.day12 :as chr]
  		  	[expectations :refer :all]
            [clojure.string :as str]))

(comment
(def file (slurp "resources/chr16/day12real"))

(expect "" (chr/parse-file file))

(expect [1 2 3 4 1] (chr/ccpy [1 2 0 4 1] "3" "c"))
(expect [1 2 2 4 2] (chr/ccpy [1 2 0 4 2] "b" "c"))

(expect [1 2 0 4 2] (chr/cjnz [1 2 0 4 2] "c" "3"))
(expect [1 2 3 4 4] (chr/cjnz [1 2 3 4 2] "b" "3"))

;(expect 317993 (chr/process file 0))
;(expect 9227647 (chr/process file 1))
)