(ns clj-compress.core
  (:require [clojure.java.io :as io])
  (:import [org.apache.commons.compress.archivers ArchiveInputStream ArchiveStreamFactory]
           [org.apache.commons.compress.archivers.zip ZipArchiveEntry]
           [java.io OutputStream FileOutputStream FileInputStream]
           [org.apache.commons.io IOUtils]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(let [zipFile (io/file "target.gz")
      fo (FileOutputStream. zipFile)
      archiveOut (-> (ArchiveStreamFactory.)
                     (.createArchiveOutputStream ArchiveStreamFactory/ZIP fo))
      files [(io/file "LICENSE")]]
  (doseq [f files]
    (.putArchiveEntry archiveOut (ZipArchiveEntry. (.getName f)))
    (IOUtils/copy (FileInputStream. f) archiveOut)
    (.closeArchiveEntry archiveOut))
  (.close archiveOut)
  (.close fo)
  zipFile)




