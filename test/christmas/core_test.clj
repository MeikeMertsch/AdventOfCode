(ns christmas.core-test
 (:require [christmas.core :as chr]
 		 	[expectations :refer :all]
 		 	[clojure.string :as str]))

(def file "resources/leaderboard.json")

;(expect "" (clojure.pprint/print-table (sort-by :stars > (vals (:members (chr/import-file file))))))

;(expect "" (first (:members (chr/import-file file))))